import { SymbolCode } from "./symbolcode";

export class Symbol {
    ticker!: string;
    name!: string;
    market!: string;
    locale!: string;
    currency!: string;
    active!: string;
    primaryExch!: string;
    type!: string;
    codes!:SymbolCode;
    updated!: string;
    url!: string;
}