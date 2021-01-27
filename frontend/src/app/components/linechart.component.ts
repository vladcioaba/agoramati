import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { interval } from "rxjs";
import {first} from "rxjs/operators";


import { QuoteService } from '../services/quote.service';
import { ChartComponent } from '@syncfusion/ej2-angular-charts';

@Component({selector: 'linechart', templateUrl: 'linechart.component.html', styleUrls: [ './linechart.component.css' ] })
export class LinechartComponent implements OnInit {
  @ViewChild('chart')
  public chart: ChartComponent;
  @Input() public ticker: string;

  private static _map:Map<string, any> = new Map<string, any>();
  
  constructor(private quoteService: QuoteService) {
    
  };

  public symbol!:string

  public primaryXAxis!: Object;
  public primaryYAxis!: Object;
  public chartArea!: Object;
  public background!: string;
  public chartData!: Object[];

  public ngOnInit():void {

    this.symbol = '' + Math.random();
    
    this.primaryXAxis = {
      majorGridLines: { width : 0 },
      maximumLabels: 1,
      visible: false
    };
    this.primaryYAxis = {
      majorGridLines: { width : 0 },
      maximumLabels: 1,
      opposedPosition: true,
      visible: false
    };
    this.chartArea = {
      border: {
          width: 0
      }
    };

    if (LinechartComponent._map.has(this.ticker)) {
      this.chartData = LinechartComponent._map.get(this.ticker);
    }

    this.background = 'rgba(255, 255, 255, 0)';

    this.loadData();
    interval(5000).pipe().subscribe(() => { this.loadData(); });
  }

  public loadData() {
    var date = new Date();    
    var t2 = date.getTime();
    var t1 = t2 -  7 * 24 * 60 * 60 * 1000;
    this.quoteService.getQuotes([this.ticker, t1.toString(), t2.toString()])
        .pipe(first())
        .subscribe(
        data => {
            LinechartComponent._map.set(this.ticker, data);
            this.chartData = data;
        },
        error => {
            console.log("e " + error)
        });
  }
}