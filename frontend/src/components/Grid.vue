<template>

  <div class="grid">
    <table class="grid-table">
      <thead>
        <tr>
          <th v-for="key in columns"
            v-bind:key="key">
            {{ key | capitalize }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="entry in gridData" v-bind:key="entry.id">
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
    filterKey: String,
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
