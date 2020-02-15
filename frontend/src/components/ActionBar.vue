<template>

  <div class="action-bar">
    <Action
      v-for="action in actions"
      :key="action.route"
      :route="action.route"
      :type="action.type"
      :label="action.label" />
  </div>

</template>

<script>

import Action from './Action.vue';

export default {
  name: 'ActionBar',
  components: {
    Action,
  },
  props: {
    actions: Array,
  },
};
</script>

<style lang="scss">
@import '../styles/action-bar.scss';
</style>
