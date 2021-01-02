export const authApi = 'http://localhost:8200'

export const backendUrl = {
  authService: {
    deleteuser: `${authApi}/user/`,
    users: `${authApi}/user/all`,
    authenticate: `${authApi}/user/authenticate`,
    register: `${authApi}/user/register`,
  }
}