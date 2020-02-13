import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import UsersView from './views/users/UsersList.vue';
import ProjectsView from './views/projects/ProjectsList.vue';

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
      component: UsersView,
    },
    {
      path: '/projects',
      name: 'projects',
      component: ProjectsView,
    },
  ],
});
