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
import ActionBar from '../../components/ActionBar.vue';

export default {
  name: 'usersView',
  components: {
    ActionBar,
  },
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
  computed: {
    actions() {
      return [
        { route: '/users', type: 'primary', label: 'Admin' },
        { route: `/users/${this.user.id}/edit`, type: 'primary', label: 'Edit' },
      ];
    },
  },
};

</script>
