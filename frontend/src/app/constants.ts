export const userApi = 'http://localhost:8200'
export const quotesApi = 'http://localhost:8500'

export const backendUrl = {
  quotesService: {
    getQuotes: `${quotesApi}/getquotes`,
    searchSymbol: `${quotesApi}/searchsymbol`,
    addSymbol: `${quotesApi}/addsymbol`,
    removeSymbol: `${quotesApi}/removesymbol`,
    getSymbolAvatars: `${quotesApi}/getsymbolavatars`,
  },
  userService: {
    authenticate: `${userApi}/authenticate`,
    register: `${userApi}/register`,
    watchlist: `${userApi}/watchlist`,
    addSymbol: `${userApi}/addsymbol`,
    removesSymbol: `${userApi}/removesymbol`,
  }
}