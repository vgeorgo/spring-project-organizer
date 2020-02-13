<template>

  <div class="user-container" v-if="user !== null">
    <h5>User information</h5>
    <div class="form-group-container">
      <div class="user-name form-group">
        <label>Name: </label>
        {{ user.name }}
      </div>
      <div v-if="user.supervisor !== null" class="user-name form-group">
        <label>Supervisor: </label>
        {{ user.supervisor.name }}
      </div>
    </div>
    <div class="clearfix"></div>
    <h5>Projects</h5>
    <div class="user-projects">
      <ul v-if="user.projects.length > 0">
        <li v-for="p in user.projects" v-bind:key="p.id">
          {{ p.name }}
        </li>
      </ul>
      <div v-else>No projecs found.</div>
    </div>
  </div>

</template>

<script>

import Api from '../../config/api';

export default {
  name: 'usersView',
  data() {
    return {
      user: null,
    };
  },
  mounted() {
    Api
      .get(`/users/${this.$route.params.id}`)
      .then((response) => { this.user = response.data; });
  },
  methods: {

  },
};

</script>
