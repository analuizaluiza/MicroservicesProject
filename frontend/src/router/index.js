import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '@/components/LoginForm.vue';
import CadastroForm from '@/components/CadastroForm.vue';
import SchoolDashboard from '@/components/SchoolDashboard.vue';
import HomePage from '@/components/HomePage.vue';  // Importando a nova tela inicial
import StudentList from '@/components/StudentList.vue';  // Página de gerenciar alunos
import SchoolList from '@/components/SchoolList.vue';  // Página de gerenciar escolas

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginForm,
  },
  {
    path: '/signup',
    name: 'cadastro',
    component: CadastroForm,
  },
  {
    path: '/',
    name: 'home',
    component: HomePage,
    meta: { requiresAuth: true },  // Página inicial após login
  },
  {
    path: '/students',
    name: 'students',
    component: StudentList,
    meta: { requiresAuth: true },
  },
  {
    path: '/schools',
    name: 'schools',
    component: SchoolList,
    meta: { requiresAuth: true },
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: SchoolDashboard,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = true;  // Simula que o usuário está autenticado

  if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
    next({ path: '/login' });  // Redireciona para o login se não estiver autenticado
  } else {
    next();
  }
});

export default router;
