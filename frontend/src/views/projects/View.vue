<template>

  <div class="project-container" v-if="project !== null">
    <ActionBar :actions="actions" />
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
import ActionBar from '../../components/ActionBar.vue';

export default {
  name: 'projectsView',
  components: {
    ActionBar,
  },
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
  computed: {
    actions() {
      return [
        { route: '/projects', type: 'primary', label: 'Admin' },
        { route: `/projects/${this.project.id}/edit`, type: 'primary', label: 'Edit' },
      ];
    },
  },
};

</script>
