﻿import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from 'src/app/models';
import { backendUrl } from 'src/app/constants';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${backendUrl.userService.users}`);
    }

    register(user: User) {
        return this.http.post(`${backendUrl.userService.register}`, user);
    }

    delete(id: number) {
        return this.http.delete(`${backendUrl.userService.deleteuser}${id}`);
    }
}