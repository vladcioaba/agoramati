import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { WatchlistData } from 'src/app/models';
import { backendUrl } from 'src/app/constants';

@Injectable({ providedIn: 'root' })
export class WatchlistService {
    constructor(private http: HttpClient) { }

    getWatchlist(userToken) {
        return this.http.post<WatchlistData[]>(`${backendUrl.userService.watchlist}`, userToken);
    }

    addSymbolToWatchlist(newSymbol) {
        return this.http.post(`${backendUrl.userService.addSymbol}`, newSymbol);
    }

    removeSymbolFromWatchlist(userToken) {
        return this.http.post(`${backendUrl.userService.removesSymbol}`, userToken);
    }
}