import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { User } from 'src/app/models';
import { UserService, AuthenticationService } from 'src/app/services';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {
    currentUser: User;
    users = [];

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService
    ) {
        this.currentUser = this.authenticationService.currentUserValue;
    }

    ngOnInit() {
       
    }
}