import { Component, OnInit } from '@angular/core';
import { IgxFilterOptions } from "igniteui-angular";

@Component({templateUrl: './dashboard.component.html', styleUrls: [ './dashboard.component.scss' ]})
export class DashboardComponent implements OnInit {
    public searchItem: string;

    public items = [
      {
        isFavorite: false,
        ticker: "AAPL",
        lastgrowth: "15%",
        photo: "https://etoro-cdn.etorostatic.com/market-avatars/aapl/150x150.png"
      },
      {
        isFavorite: true,
        ticker: "TSLA",
        lastgrowth: "5%",
        photo: "https://etoro-cdn.etorostatic.com/market-avatars/tsla/150x150.png"
      },
      {
        isFavorite: false,
        ticker: "NIO",
        lastgrowth: "8%",
        photo: "https://etoro-cdn.etorostatic.com/market-avatars/4489/150x150.png"
      }
    ];
  
    public density = "comfortable";
    public displayTimeframe;
  
    constructor() { }
  
    public ngOnInit() {
        this.density = 'compact';
        this.displayTimeframe = [
            { label: "1 D", selected: true, togglable: true },
            { label: "1 M", selected: false, togglable: true },
            { label: "3 M", selected: false, togglable: true },
            { label: "6 M", selected: false, togglable: true },
            { label: "12 M", selected: false, togglable: true }
        ];
    }
  
    public selectTimeframe(event) {
      
    }
  
    public toggleFavorite(item: any) {
      item.isFavorite = !item.isFavorite;
    }
  
    get filterItems() {
      const fo = new IgxFilterOptions();
      fo.key = "name";
      fo.inputValue = this.searchItem;
      return fo;
    }
}