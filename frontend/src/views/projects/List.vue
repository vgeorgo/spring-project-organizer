<template>

  <div>
    <ActionBar :actions="actions" />
    <Grid
      :gridData="gridData"
      :columns="gridColumns"
      :baseRoute="'/projects'">
    </Grid>
  </div>

</template>

<script>

import Grid from '../../components/Grid.vue';
import ActionBar from '../../components/ActionBar.vue';
import Api from '../../config/api';

export default {
  name: 'projectsList',
  components: {
    Grid,
    ActionBar,
  },
  data() {
    return {
      gridColumns: ['id', 'name'],
      gridData: [],
    };
  },
  mounted() {
    Api
      .get('/projects')
      .then((response) => { this.gridData = response.data; });
  },
  computed: {
    actions() {
      return [
        { route: '/projects/create', type: 'primary', label: 'Create' },
      ];
    },
  },
};

</script>
