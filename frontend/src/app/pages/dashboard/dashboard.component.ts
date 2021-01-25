import { Component, ElementRef, Input, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { IgxFilterOptions } from "igniteui-angular";
import { first } from 'rxjs/operators';
import { QuoteService } from '../../services/quote.service';
import { Avatar } from '../../models';

@Component({templateUrl: './dashboard.component.html', styleUrls: [ './dashboard.component.scss' ]})
export class DashboardComponent implements OnInit {
    public searchItem: string;
    
    @Input()
    public items = [
      {
        isFavorite: false,
        ticker: "AAPL",
        lastgrowth: "15%",
        photo: "assets/img/default_avatar.png"
      },
      {
        isFavorite: true,
        ticker: "TSLA",
        lastgrowth: "5%",
        photo: "assets/img/default_avatar.png"
      },
      {
        isFavorite: false,
        ticker: "NIO",
        lastgrowth: "8%",
        photo: "assets/img/default_avatar.png"
      }
    ];
  
    public density = "comfortable";
    public displayTimeframe;
    public symbols = ['NIO', 'TSLA', 'AAPL'];
  

    constructor(private quoteService: QuoteService) { }
  
    public ngOnInit() {
        this.density = 'compact';
        this.displayTimeframe = [
            { label: "1 D", selected: true, togglable: true },
            { label: "1 M", selected: false, togglable: true },
            { label: "3 M", selected: false, togglable: true },
            { label: "6 M", selected: false, togglable: true },
            { label: "12 M", selected: false, togglable: true }
        ];
        this.quoteService.getSymbolAvatars(this.symbols)
            .pipe(first())
            .subscribe(
                data => {
                  for (var avatar of data) {
                    for (var item of this.items) {
                      if (avatar.avatarId == item.photo) {
                        item.photo = avatar.avatarUrl;
                      }
                    }
                  }
                  
                  // this.items.push({isFavorite: false,
                  //   ticker: "NIO",
                  //   lastgrowth: "8%",
                  //   photo: "assets/img/default_avatar.png"})
                },
                error => {
                    
                });
    }
  
    public selectTimeframe(event) {
      
    }
  
    public toggleFavorite(item: any) {
      item.isFavorite = !item.isFavorite;
    }

    public onDeleteClicked(event) {
      console.log("click " + event)
    }
  
    get filterItems() {
      const fo = new IgxFilterOptions();
      fo.key = "name";
      fo.inputValue = this.searchItem;
      return fo;
    }
}