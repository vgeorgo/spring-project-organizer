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
      path: '/users/create',
      name: 'User Create',
      component: UsersForm,
    },
    {
      path: '/users/:id/edit',
      name: 'User Edit',
      component: UsersForm,
    },
    {
      path: '/users/:id',
      name: 'User View',
      component: UsersView,
    },
    {
      path: '/projects',
      name: 'Projects',
      component: ProjectsList,
    },
    {
      path: '/projects/create',
      name: 'Project Create',
      component: ProjectsForm,
    },
    {
      path: '/projects/:id/edit',
      name: 'Project Edit',
      component: ProjectsForm,
    },
    {
      path: '/projects/:id',
      name: 'Project View',
      component: ProjectsView,
    },
  ],
});
