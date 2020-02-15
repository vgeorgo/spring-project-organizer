<template>

  <div>
    <ActionBar :actions="actions" />
    <Grid
      :gridData="gridData"
      :columns="gridColumns"
      :baseRoute="'/users'">
    </Grid>
  </div>

</template>

<script>

import Grid from '../../components/Grid.vue';
import ActionBar from '../../components/ActionBar.vue';
import Api from '../../config/api';

export default {
  name: 'usersList',
  components: {
    Grid,
    ActionBar,
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
  computed: {
    actions() {
      return [
        { route: '/users/create', type: 'primary', label: 'Create' },
      ];
    },
  },
};

</script>
