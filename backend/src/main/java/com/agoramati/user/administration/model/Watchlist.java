package com.agoramati.user.administration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "watchlist_table")
//public class Watchlist {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "entry_id", updatable = false, nullable = false)
//    private Long entryId;
//
//    @Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(columnDefinition = "position")
//    private int position;
//
//    @Column(columnDefinition = "stock_symbol")
//    private String stock_symbol;
//
//    @Column(columnDefinition = "stock_name")
//    private String stock_name;
//}
