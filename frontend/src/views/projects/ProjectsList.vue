<template>

  <Grid
    :gridData="gridData"
    :columns="gridColumns">
  </Grid>

</template>

<script>

import Grid from '../../components/Grid.vue';
import Api from '../../config/api';

export default {
  name: 'projectsView',
  components: {
    Grid,
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
  methods: {

  },
};

</script>
