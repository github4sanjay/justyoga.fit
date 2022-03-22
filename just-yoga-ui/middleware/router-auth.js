export default function ({ store, redirect, route }) {
  if (store.getters['login/user'] != null && route.name === 'login') {
    redirect('/')
  }
  if (store.getters['login/user'] == null && isAdminRoute(route)) {
    redirect('/login')
  }
}

function isAdminRoute (route) {
  if (route.matched.some(record => record.path === '/admin')) {
    return true
  }
}
