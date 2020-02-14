<template>

  <div class="project-container">
    <h5>User information</h5>
    <div class="form-group-container">
      <div class="form-group">
        <label>Name: </label><br/>
        <input type="text" v-model="user.name">
      </div>
      <div class="form-group">
        <label>Type: </label><br/>
        <select v-model="user.type" v-on:change="onChangeType">
          <option value="developer">Developer</option>
          <option value="supervisor">Supervisor</option>
        </select>
      </div>
      <div class="form-group" v-if="user.type === 'developer'">
        <label>Supervisor: </label><br/>
        <select v-model="user.supervisor.id">
          <option value="">Select</option>
          <option
            v-bind:key="supervisor.id"
            v-for="supervisor in supervisors"
            :value="supervisor.id">
            {{supervisor.name}}
          </option>
        </select>
      </div>
    </div>
    <div class="clearfix"></div>
    <button type="button" v-on:click="save">Save</button>
  </div>

</template>

<script>

import Api from '../../config/api';

export default {
  name: 'usersForm',
  data() {
    return {
      supervisors: [],
      user: {
        id: null,
        name: '',
        type: 'developer',
        supervisor: {
          id: null,
        },
      },
    };
  },
  mounted() {
    this.loadSupervisors();
    if (!this.$route.params || !this.$route.params.id) { return; }
    this.loadUser();
  },
  methods: {
    loadSupervisors() {
      Api
        .get('/supervisors')
        .then((response) => { this.supervisors = response.data; });
    },
    loadUser() {
      Api
        .get(`/users/${this.$route.params.id}`)
        .then((response) => {
          this.user = response.data;
          if (!this.user.supervisor) { this.user.supervisor = { id: null }; }
        });
    },
    save() {
      const params = {
        name: this.user.name,
        type: this.user.type,
        supervisor: this.user.supervisor.id
          ? { id: this.user.supervisor.id }
          : null,
      };

      if (this.user.id) {
        Api
          .put(`/users/${this.user.id}`, params)
          .then((response) => { this.onSaveSuccess(response.data); });
      } else {
        Api
          .post('/users', params)
          .then((response) => { this.onSaveSuccess(response.data); });
      }
    },
    onSaveSuccess(userData) {
      this.$router.push(`/users/${userData.id}`);
    },
    onChangeType() {
      if (this.user.type === 'supervisor') { this.user.supervisor = { id: null }; }
    },
  },
};

</script>
