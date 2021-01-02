export const authApi = 'http://localhost:8200'
export const quotesApi = 'http://localhost:8500'

export const backendUrl = {
  quotesService: {
    getQuotes: `${quotesApi}/getquotes`,
  },
  userService: {
    deleteuser: `${authApi}/user/`,
    users: `${authApi}/user/all`,
    authenticate: `${authApi}/user/authenticate`,
    register: `${authApi}/user/register`,
  }
}