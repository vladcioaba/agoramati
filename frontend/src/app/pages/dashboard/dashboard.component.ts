import { Component, OnInit } from '@angular/core';

@Component({templateUrl: './dashboard.component.html', styleUrls: [ './dashboard.component.css' ]})
export class DashboardComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }

    public dataSource: Object = [
        { id: '1' },
    ];

    public fields: Object = { groupBy: 'id' };
    
    loop(number:number) {
        return Array(number).fill(0).map((x, index) => (x + 1) * index);
    }
}