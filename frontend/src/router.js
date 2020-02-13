import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import UsersList from './views/users/List.vue';
import UsersView from './views/users/View.vue';
import ProjectsList from './views/projects/List.vue';
import ProjectsView from './views/projects/View.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/users',
      name: 'users',
      component: UsersList,
    },
    {
      path: '/users/:id',
      name: 'users View',
      component: UsersView,
    },
    {
      path: '/projects',
      name: 'projects',
      component: ProjectsList,
    },
    {
      path: '/projects/:id',
      name: 'projects View',
      component: ProjectsView,
    },
  ],
});
