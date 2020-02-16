<template>

  <div class="user-container" v-if="user !== null">
    <ActionBar :actions="actions" />
    <h5>User information</h5>
    <div class="form-group-container">
      <div class="form-group">
        <label for="user_name">Name: </label><br />
        {{ user.name }}
      </div>
      <div class="form-group">
        <label for="user_type">Type: </label><br />
        {{ user.type }}
      </div>
      <div v-if="user.supervisor !== null" class="user-name form-group">
        <label>Supervisor: </label><br />
        {{ user.supervisor.name }}
      </div>
    </div>
    <div class="clearfix"></div>
    <h5 v-if="user.subordinates">Subordinates</h5>
    <div v-if="user.subordinates" class="user-subordinates">
      <ul v-if="user.subordinates.length > 0">
        <li v-for="s in user.subordinates" v-bind:key="s.id">
          {{ s.id+': '+s.name }}
        </li>
      </ul>
      <div v-else>No subordinates found.</div>
    </div>
    <h5>Projects</h5>
    <div class="user-projects">
      <div class="in-projetcs list-projects-container col-xs-4">
        <h6 v-if="user.type == 'developer'">Participating</h6>
        <ul v-if="participatingProjects.length > 0">
          <li v-for="p in participatingProjects" v-bind:key="p.id">
            <button type="button"
              class="btn btn-danger btn-sm"
              v-on:click="() => {removeProject(p)}"
              v-if="user.type == 'developer'">
              <i class="fa fa-trash" aria-hidden="true"></i>
            </button>
            {{ p.name }}
          </li>
        </ul>
        <div v-else>No projecs found.</div>
      </div>
      <div class="available-projetcs" v-if="user.type == 'developer'">
        <h6>Available</h6>
        <ul v-if="availableProjects.length > 0">
          <li v-for="p in availableProjects" v-bind:key="p.id">
            <button type="button"
              class="btn btn-success btn-sm"
              v-on:click="() => {addProject(p)}">
              <i class="fa fa-angle-left" aria-hidden="true"></i>
            </button>
            {{ p.name }}
          </li>
        </ul>
        <div v-else>No projecs found.</div>
      </div>
    </div>
  </div>

</template>

<script>

import Api from '../../config/api';
import ActionBar from '../../components/ActionBar.vue';

export default {
  name: 'usersView',
  components: {
    ActionBar,
  },
  data() {
    return {
      user: null,
      allProjects: [],
    };
  },
  mounted() {
    Api
      .get(`/users/${this.$route.params.id}`)
      .then((response) => { this.user = response.data; });
    Api
      .get('/projects')
      .then((response) => { this.allProjects = response.data; });
  },
  computed: {
    participatingProjects() {
      if (!this.user) { return []; }
      if (this.user.type === 'developer') { return this.user.projects; }

      return this.allProjects.filter((p) => p.leader.id === this.user.id);
    },
    actions() {
      return [
        { route: '/users', type: 'primary', label: 'Admin' },
        { route: `/users/${this.user.id}/edit`, type: 'primary', label: 'Edit' },
      ];
    },
    availableProjects() {
      return this.allProjects.filter((p) => {
        let found = false;
        if (this.user && this.user.projects) {
          this.user.projects.forEach((up) => {
            if (p.id === up.id) { found = true; }
          });
        }

        return !found;
      });
    },
  },
  methods: {
    addProject(project) {
      Api
        .put(`/projects/${project.id}/developers/${this.user.id}`)
        .then(() => { this.user.projects = [...this.user.projects, project]; });
    },
    removeProject(project) {
      Api
        .delete(`/projects/${project.id}/developers/${this.user.id}`)
        .then(() => {
          this.user.projects = this.user.projects.filter((p) => p.id !== project.id);
        });
    },
  },
};

</script>
