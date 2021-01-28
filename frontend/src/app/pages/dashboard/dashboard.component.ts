import { Component, Input, OnInit, Pipe, PipeTransform, ViewEncapsulation } from '@angular/core';
import { ConnectedPositioningStrategy, IgxFilterOptions, VerticalAlignment } from "igniteui-angular";
import { debounceTime, distinctUntilChanged, first } from 'rxjs/operators';
import { Avatar, Symbol } from '../../models';
import { QuoteService, WatchlistService, AuthenticationService } from 'src/app/services';
import { Subject, Subscription } from 'rxjs';

@Component({selector: "igxFor-list", 
            templateUrl: './dashboard.component.html', 
            styleUrls: [ './dashboard.component.scss' ]})
export class DashboardComponent implements OnInit {
    public symbolResults = [];

    private _searchItem: string;
    private _timerId;

    @Input() set searchItem(value: string) {
        this._searchItem = value;
        clearTimeout(this._timerId);
        this._timerId = setTimeout( () => {
            console.log("searchItem " + value);
            if (value && value.length > 0) {
                this.findTickerByName(value);
            }
        }, 1500);
    }
    
    get searchItem(): string {    
        return this._searchItem;
    }

    private _symbolResultSelected:Symbol;

    @Input() set symbolResultSelected(value: Symbol) {
        this.symbolResults = []
        this._symbolResultSelected = value;
        console.log("symbolResultSelected " + value);
        //add to list and reload
        var newItems = this.items.slice();
        newItems.push({
            isFavorite: false,
            ticker: value.ticker,
            name: value.name,
            photo: "assets/img/default_avatar.png"
        });
        let token = this.authenticationService.currentUserValue.token;
        this.watchlistService.addSymbolToWatchlist({"token": token, "symbol": value.ticker, "name": value.name})
            .pipe(first())
            .subscribe(
                data => {                    
                    this.reloadList(newItems);
                    this.quoteService.addsymbol(value.ticker)
                    .pipe(first())
                    .subscribe(
                        data => {
                            console.log("addSymbolToWatchlist  " + this.authenticationService.currentUserValue.username + " " + value.ticker)
                        },
                        error => {
                            console.log("e " + error)
                        });
                },
                error => { console.log("e " + error) });       
    }
    
    get symbolResultSelected(): Symbol {    
        return this._symbolResultSelected;
    }

    @Input()
    public items = [];
  
    public density = "comfortable";
    public displayTimeframe;

    public settings = {
        positionStrategy: new ConnectedPositioningStrategy({
            closeAnimation: null,
            openAnimation: null,
            verticalDirection: VerticalAlignment.Bottom,
            verticalStartPoint: VerticalAlignment.Bottom
        })
    };

    constructor(private quoteService: QuoteService,
                private watchlistService: WatchlistService,
                private authenticationService: AuthenticationService) { }
  
    public ngOnInit() {
        this.density = 'compact';
        this.displayTimeframe = [
            { label: "1 D", selected: true, togglable: true },
            { label: "1 M", selected: false, togglable: true },
            { label: "3 M", selected: false, togglable: true },
            { label: "6 M", selected: false, togglable: true },
            { label: "12 M", selected: false, togglable: true }
        ];

        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        let token = this.authenticationService.currentUserValue.token;
        console.log(token);
        this.watchlistService.getWatchlist({"token": token})
            .pipe(first()).subscribe(
                data => {
                    let newData = []; 
                    data.forEach(item => {
                        newData.push({
                            isFavorite: false,
                            ticker: item.stockId,
                            name: item.stockName,
                            photo: "assets/img/default_avatar.png"
                        });
                    });
                    this.reloadList(newData); 
                },
                error => { console.log("e " + error); });
    }
  
    public ngOnDestroy() {
    }

    public selectTimeframe(event) {
        console.log("selectTimeframe " + event)
    }
  
    public toggleFavorite(item: any) {
        item.isFavorite = !item.isFavorite;
    }

    public onDeleteClicked(symbol) {
        console.log("click " + symbol);
        let token = this.authenticationService.currentUserValue.token;
        let newItems = this.items.filter(item => { return item.ticker !== symbol });
        this.watchlistService.removeSymbolFromWatchlist({"token": token, "symbol": symbol})
            .pipe(first())
            .subscribe(
                data => {
                    this.reloadList(newItems);
                },
                error => {
                    console.log("e " + error)
                });
        this.quoteService.removeSymbol(symbol)
            .pipe(first())
            .subscribe(
                data => {
                },
                error => {
                    console.log("e " + error)
                });
    }
  
    public reloadList(data) {
        this.items = data;
        let symbols = [];
        this.items.forEach(item => {     
            symbols.push(item.ticker);
        });
        this.quoteService.getSymbolAvatars(symbols)
            .pipe(first())
            .subscribe(
                data => {
                    this.items.forEach(item => {
                        for (var avatar of data) {
                            if (avatar.avatarId == item.ticker) {
                                item.photo = avatar.avatarUrl;
                            }
                        }
                    });
                },
                error => {
                    console.log("e " + error);
                });
    }

    public onSearchTickers(value) {
        console.log("onSearchTickers " + value);
    }

    public findTickerByName(name:string) {
        this.quoteService.findSymbolByName(name).pipe(first())
        .subscribe(
            data => {
                //console.log("d " + data);
                this.symbolResults = [];
                data.forEach(symbol => {
                    console.log(symbol);
                    this.symbolResults.push(symbol);
                });
            },
            error => {
                console.log("e " + error);
            });
    }

    public filterItems(list) {
        const fo = new IgxFilterOptions();
        fo.key = "name";
        fo.inputValue = list;
        return fo;
    }
}
