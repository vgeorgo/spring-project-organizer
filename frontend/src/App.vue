<template>

  <div id="wrapper">
    <Menu></Menu>

    <div class="main-content">
      <div class="main-content__top">
          <h1 class="main-content__title">
              {{ sectionName }}
          </h1>
      </div>
      <div class="main-content__body">
        <router-view></router-view>
      </div>
    </div>
  </div>

</template>

<script>

import startCase from 'lodash/startCase';
import Menu from './components/Menu.vue';

export default {
  components: {
    Menu,
  },
  computed: {
    sectionName() {
      return startCase(this.$route.name);
    },
  },
};

</script>

<style lang="scss">
@import 'styles/layout.scss';
@import 'styles/media-queries.scss';
</style>
