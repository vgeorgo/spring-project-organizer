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
  name: 'usersView',
  components: {
    Grid,
  },
  data() {
    return {
      gridColumns: ['id', 'name', 'type'],
      gridData: [],
    };
  },
  mounted() {
    Api
      .get('/users')
      .then((response) => { this.gridData = response.data; });
  },
  methods: {

  },
};

</script>
