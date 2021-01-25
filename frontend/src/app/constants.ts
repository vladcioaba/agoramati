export const authApi = 'http://localhost:8200'
export const quotesApi = 'http://localhost:8500'

export const backendUrl = {
  quotesService: {
    getQuotes: `${quotesApi}/getquotes`,
  },
  userService: {
    authenticate: `${authApi}/authenticate`,
    register: `${authApi}/register`,
  }
}