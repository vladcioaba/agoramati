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
  @Input() public ticker: string;
  
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

    this.background = 'rgba(255, 255, 255, 0)';

    this.quoteService.getQuotes([this.ticker])
        .pipe(first())
        .subscribe(
        data => {
            console.log("d " + data)
            this.chartData = data;
        },
        error => {
            console.log("e " + error)
        });
    /*
    interval(2000).pipe(
        startWith(0),
        switchMap(() => this.http.get(backendUrl.quotesService.getQuotes, { params: { } }))
    );
    */
  }

}