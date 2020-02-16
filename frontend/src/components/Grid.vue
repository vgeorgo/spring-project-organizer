<template>

  <div class="grid">
    <table class="grid-table">
      <thead>
        <tr>
          <th>Actions</th>
          <th v-for="key in columns"
            v-bind:key="key">
            {{ key | capitalize }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="entry in gridData" v-bind:key="entry.id">
          <td>
            <router-link v-bind:to="`${baseRoute}/${entry.id}`">
              <i class="fa fa-eye" aria-hidden="true"></i>
            </router-link>
            <router-link v-bind:to="`${baseRoute}/${entry.id}/edit`">
              <i class="fa fa-edit" aria-hidden="true"></i>
            </router-link>
          </td>
          <td v-for="key in columns" v-bind:key="key">
            {{entry[key]}}
          </td>
        </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
export default {
  name: 'Grid',
  props: {
    gridData: Array,
    columns: Array,
    baseRoute: String,
  },
  filters: {
    capitalize(str) {
      return str.charAt(0).toUpperCase() + str.slice(1);
    },
  },
  methods: {
  },
};
</script>

<style lang="scss">
@import '../styles/grid.scss';
</style>
