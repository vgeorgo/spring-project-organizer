<template>

  <Grid
    :gridData="gridData"
    :columns="gridColumns"
    :baseRoute="'/users'">
  </Grid>

</template>

<script>

import Grid from '../../components/Grid.vue';
import Api from '../../config/api';

export default {
  name: 'usersList',
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
