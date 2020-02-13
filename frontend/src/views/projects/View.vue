<template>

  <div class="project-container" v-if="project !== null">
    <h5>Project information</h5>
    <div class="form-group-container">
      <div class="form-group">
        <label>Name: </label><br/>
        {{ project.name }}
      </div>
      <div v-if="project.leader !== null" class="project-name form-group">
        <label>Leader: </label><br/>
        {{ project.leader.name }}
      </div>
    </div>
    <div class="clearfix"></div>
    <h5>Developers</h5>
    <div class="user-projects">
      <ul v-if="project.developers.length > 0">
        <li v-for="d in project.developers" v-bind:key="d.id">
          {{ d.name }}
        </li>
      </ul>
      <div v-else>No developers found.</div>
    </div>
  </div>

</template>

<script>

import Api from '../../config/api';

export default {
  name: 'projectsView',
  data() {
    return {
      project: null,
    };
  },
  mounted() {
    Api
      .get(`/projects/${this.$route.params.id}`)
      .then((response) => { this.project = response.data; });
  },
  methods: {

  },
};

</script>
