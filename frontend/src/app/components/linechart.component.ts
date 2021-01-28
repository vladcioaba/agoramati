import { Component, OnInit, Input, ViewChild, OnDestroy, SimpleChanges, OnChanges, ViewEncapsulation } from '@angular/core';
import { interval } from "rxjs";
import {first} from "rxjs/operators";
import {chart_cache_map} from '../globals'; 

import { QuoteService } from '../services/quote.service';
import { ChartComponent } from '@syncfusion/ej2-angular-charts';

@Component({selector: 'linechart',
            templateUrl: 'linechart.component.html',
            styleUrls: [ './linechart.component.css' ],
            encapsulation: ViewEncapsulation.None })
export class LinechartComponent implements OnInit, OnDestroy, OnChanges {
  @ViewChild('chart')
  public chart: ChartComponent;
  @Input() public ticker: string;
  
  constructor(private quoteService: QuoteService) {
    
  };

  public primaryXAxis!: Object;
  public primaryYAxis!: Object;
  public chartArea!: Object;
  public background!: string;
  public chartData!: Object[];
  public enableCanvas!: Boolean;
  public animation!: Object

  private _interval:any;
  private _subscriptionTimer:any;
  private _subscriptionQuery:any;
  private _date:Date = new Date();    
    
  public ngOnInit():void {
    console.log("ngOnInit " + this.ticker);

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
    this.animation = { enable: false };
    this.chartArea = {
      border: {
          width: 0,
      }
    };

    this.enableCanvas = true;

    this.background = 'rgba(255, 255, 255, 0)';
    // chart_cache_map.forEach((value: string, key: string) => {
    //     console.log("[key: " + key + "]");
    // });

    if (chart_cache_map.has(this.ticker)) {
        //console.log("Load from cache " + this.ticker);
        this.chartData = chart_cache_map.get(this.ticker);
    } else {
        //console.log("Load from server " + this.ticker);
        this.loadData();
    }

    this._interval = interval(2000);
    this._subscriptionTimer = this._interval.pipe().subscribe(() => { 
       this.loadData(); 
    });    
  }

  public ngOnDestroy() {
    console.log("ngOnDestroy " + this.ticker);
    this._subscriptionTimer.unsubscribe();
    if (this._subscriptionQuery) {
        this._subscriptionQuery.unsubscribe();
    }
    this.chartData = null;
  }

  public ngOnChanges(changes: SimpleChanges): void {
    console.log("ngOnChanges " + this.ticker);
    if (chart_cache_map.has(this.ticker)) {
        //console.log("Load from cache " + this.ticker);
        this.chartData = chart_cache_map.get(this.ticker);
    } else {
        //console.log("Load from server " + this.ticker);
        this.loadData();
    }
  }

  public loadData() {
    var t2 = this._date.getTime();
    var t1 = t2 -  7 * 24 * 60 * 60 * 1000;
    this._subscriptionQuery = this.quoteService.getQuotes([this.ticker, t1.toString(), t2.toString()])
        .pipe(first())
        .subscribe(
        data => {
            //console.log("TS: " + this.ticker + " " + data.length);
            chart_cache_map.set(this.ticker, data);
            this.chartData = data;
        },
        error => {
            console.log("e " + error);
        });
  }
}