import { Component, OnInit, Input, ViewChild } from '@angular/core';
//import { ILoadedEventArgs, ChartTheme } from '@syncfusion/ej2-angular-charts';
import { interval, Observable } from "rxjs";
import {first, startWith, switchMap} from "rxjs/operators";


import { QuoteService } from '../services/quote.service';
import { ChartComponent } from '@syncfusion/ej2-angular-charts';

//import { StockChart } from '@syncfusion/ej2-charts';
//import { DateTime, SplineSeries } from '@syncfusion/ej2-charts';

//StockChart.Inject(DateTime, SplineSeries);

//Chart.Inject(LineSeries, Category);

export let _chartData: any[] = [
  {
      "vw": 80.2125,
      "c": 80.4625,
      "t": 1590984000000,
      "v": 8.1000752E7,
      "h": 80.5875,
      "l": 79.3025,
      "n": 231707,
      "o": 79.4375
  },
  {
      "vw": 80.3801,
      "c": 80.835,
      "t": 1591070400000,
      "v": 8.7642632E7,
      "h": 80.86,
      "l": 79.7325,
      "n": 226248,
      "o": 80.1863
  },
  {
      "vw": 81.084,
      "c": 81.28,
      "t": 1591156800000,
      "v": 1.04490584E8,
      "h": 81.55,
      "l": 80.575,
      "n": 258867,
      "o": 81.165
  },
  {
      "vw": 80.8328,
      "c": 80.58,
      "t": 1591243200000,
      "v": 8.7560364E7,
      "h": 81.405,
      "l": 80.195,
      "n": 237171,
      "o": 81.0975
  },
  {
      "vw": 82.2786,
      "c": 82.875,
      "t": 1591329600000,
      "v": 1.37248048E8,
      "h": 82.9375,
      "l": 80.8075,
      "n": 351615,
      "o": 80.8375
  },
  {
      "vw": 82.7662,
      "c": 83.365,
      "t": 1591588800000,
      "v": 9.56509E7,
      "h": 83.4,
      "l": 81.83,
      "n": 261203,
      "o": 82.5625
  },
  {
      "vw": 85.3634,
      "c": 85.9975,
      "t": 1591675200000,
      "v": 1.47712228E8,
      "h": 86.4025,
      "l": 83.0025,
      "n": 402768,
      "o": 83.035
  },
  {
      "vw": 87.8736,
      "c": 88.21,
      "t": 1591761600000,
      "v": 1.6664698E8,
      "h": 88.6925,
      "l": 86.5225,
      "n": 449782,
      "o": 86.975
  },
  {
      "vw": 85.7741,
      "c": 83.975,
      "t": 1591848000000,
      "v": 2.01659684E8,
      "h": 87.765,
      "l": 83.87,
      "n": 577299,
      "o": 87.3275
  },
  {
      "vw": 85.0132,
      "c": 84.7,
      "t": 1591934400000,
      "v": 2.00145124E8,
      "h": 86.95,
      "l": 83.5558,
      "n": 514308,
      "o": 86.18
  },
  {
      "vw": 84.8782,
      "c": 85.7475,
      "t": 1592193600000,
      "v": 1.3880872E8,
      "h": 86.42,
      "l": 83.145,
      "n": 368615,
      "o": 83.3125
  },
  {
      "vw": 87.7181,
      "c": 88.02,
      "t": 1592280000000,
      "v": 1.65428728E8,
      "h": 88.3,
      "l": 86.18,
      "n": 446656,
      "o": 87.865
  },
  {
      "vw": 88.4283,
      "c": 87.8975,
      "t": 1592366400000,
      "v": 1.14388504E8,
      "h": 88.85,
      "l": 87.7725,
      "n": 321829,
      "o": 88.7875
  }
]

/*
let stockChart: StockChart = new StockChart({
    series: [
        {
            dataSource: chartData,
            type: 'Spline', bearFillColor: '#00226C', bullFillColor: "#0450C2", fill: 'blue'
        },
    ],
    height: '350px',
    width: '1800px'
});
*/
@Component({selector: 'linechart', templateUrl: 'linechart.component.html', styleUrls: [ './linechart.component.css' ] })
export class LinechartComponent implements OnInit {
  @ViewChild('chart')
  public chart: ChartComponent;
  @Input() public ticker: String;
  
  constructor(private quoteService: QuoteService) {
    
  };

  public symbol!:String

  public primaryXAxis!: Object;
  public primaryYAxis!: Object;
  public chartArea!: Object;
  public background!: String;
  public chartData!: Object[];

  public ngOnInit():void {

    this.symbol = '' + Math.random();
    
    //this.chartData = _chartData;
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

    this.background = 'rgba(255, 255, 255, 0)';

    this.quoteService.getQuotes([this.ticker])
            .pipe(first())
            .subscribe(
                data => {
                  this.chartData = data;
                },
                error => {
                    
                });
    /*
    interval(2000).pipe(
        startWith(0),
        switchMap(() => this.http.get(backendUrl.quotesService.getQuotes, { params: { } }))
    );
    */
  }

}