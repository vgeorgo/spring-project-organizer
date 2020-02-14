import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import UsersList from './views/users/List.vue';
import UsersView from './views/users/View.vue';
import UsersForm from './views/users/Form.vue';
import ProjectsList from './views/projects/List.vue';
import ProjectsView from './views/projects/View.vue';
import ProjectsForm from './views/projects/Form.vue';

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
      path: '/users/create',
      name: 'users Create',
      component: UsersForm,
    },
    {
      path: '/users/:id/edit',
      name: 'users Edit',
      component: UsersForm,
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
    {
      path: '/projects/create',
      name: 'projects Create',
      component: ProjectsForm,
    },
    {
      path: '/projects/:id/edit',
      name: 'projects Edit',
      component: ProjectsForm,
    },
  ],
});
