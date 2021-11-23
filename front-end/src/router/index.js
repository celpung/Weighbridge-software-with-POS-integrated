import { createRouter, createWebHistory } from 'vue-router'
import store from '../store';
import Home from '../views/Home.vue'
import Login from '../views/users/Login.vue'
import Register from '../views/users/Register.vue'
import Profile from '../views/users/Profile.vue'
import Logout from '../views/users/Logout.vue'
import Dashboard from '../views/Dashboard.vue'
import EditData from '../views/EditData.vue'
import Products from '../views/settings/Products.vue'
import Customers from '../views/settings/Customers.vue'
import Suppliers from '../views/settings/Suppliers.vue'
import Users from '../views/users/User.vue'
import Patty from '../views/report/Patty.vue'
import KAS from '../views/report/KAS.vue'
import Bank from '../views/report/Bank.vue'
import Transaction from '../views/report/Transaction.vue'
import Report from '../views/report/Report.vue'
import Expense from '../views/report/Expense.vue'
import Income from '../views/report/Income.vue'
import ProfitLost from '../views/report/ProfitLost.vue'

const authGuard = (to, from, next) => {
  if (store.getters.isAuthenticated) {
    next();
  } else {
    next("/login")
  }
};

const authAdmin = (to, from, next) => {
  if (store.getters.isAuthenticated && store.getters.isAdmin) {
    next();
  } else if (store.getters.isAuthenticated) {
    alert("Anda tidak memiliki akses");
    return;
  } else {
    next("/login")
  }
};

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: "Login"
    },
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      title: "Registrasi"
    },
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    beforeEnter: authGuard,
    meta: {
      title: "Profil"
    },
  },
  {
    path: '/logout',
    name: 'Logout',
    component: Logout,
    meta: {
      title: "Logout"
    },
  },
  {
    path: '/',
    name: 'Home',
    beforeEnter: authGuard,
    component: Home,
    meta: {
      title: "Home"
    },
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    beforeEnter: authGuard,
    component: Dashboard,
    meta: {
      title: "Dashboard"
    },
  },
  {
    path: '/edit-data',
    name: 'EditData',
    component: EditData,
    beforeEnter: authAdmin,
    meta: {
      title: "Edit Data"
    },
  },
  {
    path: '/products',
    name: 'Products',
    component: Products,
    beforeEnter: authGuard,
    meta: {
      title: "Barang"
    },
  },
  {
    path: '/customers',
    name: 'Customers',
    component: Customers,
    beforeEnter: authGuard,
    meta: {
      title: "Kostumer"
    },
  },
  {
    path: '/suppliers',
    name: 'Suppliers',
    component: Suppliers,
    beforeEnter: authGuard,
    meta: {
      title: "Supplier"
    },
  },
  {
    path: '/user',
    name: 'Users',
    component: Users,
    beforeEnter: authAdmin,
    meta: {
      title: "User"
    },
  },
  {
    path: '/patty',
    name: 'Patty',
    component: Patty,
    beforeEnter: authGuard,
    meta: {
      title: "Patty Cash"
    },
  },  
  {
    path: '/kas',
    name: 'KAS',
    component: KAS,
    beforeEnter: authGuard,
    meta: {
      title: "KAS"
    },
  },  
  {
    path: '/bank',
    name: 'Bank',
    component: Bank,
    beforeEnter: authGuard,
    meta: {
      title: "Bank"
    },
  },  
  {
    path: '/transaction',
    name: 'Transaction',
    component: Transaction,
    beforeEnter: authGuard,
    meta: {
      title: "Transaksi"
    },
  },
  {
    path: '/report',
    name: 'Report',
    component: Report,
    beforeEnter: authGuard,
    meta: {
      title: "Laporan"
    },
  },
  {
    path: '/expense',
    name: 'Expense',
    component: Expense,
    beforeEnter: authGuard,
    meta: {
      title: "Pengeluaran"
    },
  },
  {
    path: '/income',
    name: 'Income',
    component: Income,
    beforeEnter: authGuard,
    meta: {
      title: "Pemasukan"
    },
  },
  {
    path: '/pl',
    name: 'ProfitLost',
    component: ProfitLost,
    beforeEnter: authGuard,
    meta: {
      title: "Laba Rugi"
    },
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
