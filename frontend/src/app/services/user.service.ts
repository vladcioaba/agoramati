import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from 'src/app/models';
import { backendUrl } from 'src/app/constants';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    register(user: User) {
        return this.http.post(`${backendUrl.userService.register}`, user);
    }
}