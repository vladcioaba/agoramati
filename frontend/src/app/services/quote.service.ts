import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { interval } from "rxjs";
import {startWith, switchMap} from "rxjs/operators";
import { backendUrl } from '../constants';
import { Avatar, Quote } from 'src/app/models';

@Injectable({ providedIn: 'root' })
export class QuoteService {

    constructor(
        private http: HttpClient
    ) { }

    getQuotes(symbols: string[]) {
        return this.http.post<Quote[]>(`${backendUrl.quotesService.getQuotes}`, symbols);// as Observable<string[]>
    }

    getSymbolAvatars(symbols: string[]) {
        return this.http.post<Avatar[]>(`${backendUrl.quotesService.getSymbolAvatars}`, symbols);// as Observable<string[]>
    }

    findSymbolByName(symbol: string) {
        return this.http.post<Symbol[]>(`${backendUrl.quotesService.searchSymbol}`, symbol);// as Observable<string[]>
    }

    addsymbol(symbol: string) {
        return this.http.post(`${backendUrl.quotesService.addSymbol}`, symbol);// as Observable<string[]>
    }

    removeSymbol(symbol: string) {
        return this.http.post(`${backendUrl.quotesService.removeSymbol}`, symbol);// as Observable<string[]>
    }
/*
    getFxRatePolling(primaryCcy: string, secondaryCcy: string) {
        return interval(2000)
            .pipe(
                startWith(0),
                switchMap(() => this.http.get(backendUrl.quoteService.getFxRate, { params: { primaryCcy, secondaryCcy } })
            )
        ) as Observable<Rate>
    }
*/
}