<template>

  <div class="project-container">
    <h5>Project information</h5>
    <div class="form-group-container">
      <div class="form-group">
        <label>Name: </label><br/>
        <input type="text" v-model="project.name">
      </div>
      <div class="form-group">
        <label>Leader: </label><br/>
        <select v-model="project.leader.id">
          <option value="">Select</option>
          <option v-bind:key="leader.id" v-for="leader in leaders" :value="leader.id">
            {{leader.name}}
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
  name: 'projectsForm',
  data() {
    return {
      leaders: [],
      project: {
        id: null,
        name: '',
        leader: {
          id: null,
        },
      },
    };
  },
  mounted() {
    this.loadLeaders();
    if (!this.$route.params || !this.$route.params.id) { return; }
    this.loadProject();
  },
  methods: {
    loadLeaders() {
      Api
        .get('/supervisors')
        .then((response) => { this.leaders = response.data; });
    },
    loadProject() {
      Api
        .get(`/projects/${this.$route.params.id}`)
        .then((response) => { this.project = response.data; });
    },
    save() {
      const params = {
        name: this.project.name,
        leader: this.project.leader.id
          ? { id: this.project.leader.id }
          : null,
      };

      if (this.project.id) {
        Api
          .put(`/projects/${this.project.id}`, params)
          .then((response) => { this.onSaveSuccess(response.data); });
      } else {
        Api
          .post('/projects', params)
          .then((response) => { this.onSaveSuccess(response.data); });
      }
    },
    onSaveSuccess(projectData) {
      this.$router.push(`/projects/${projectData.id}`);
    },
  },
};

</script>
