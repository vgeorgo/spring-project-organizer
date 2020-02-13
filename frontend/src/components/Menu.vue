<template>

  <div class="menu-container">
    <ul class="menu">
      <li class="menu__top">
        <router-link v-bind:to="'/'" class="menu__title">
          Project Organizer
        </router-link>
      </li>

      <li>
        <router-link v-bind:to="'/'" :class="highlightSection('home')">
          <i class="fa fa-home menu__icon" aria-hidden="true"></i>
          Home
        </router-link>
      </li>

      <li>
        <router-link v-bind:to="'/users'" :class="highlightSection('users')">
          <i class="fa fa-users menu__icon" aria-hidden="true"></i>
          Users
        </router-link>
      </li>

      <li>
        <router-link v-bind:to="'/projects'" :class="highlightSection('projects')">
          <i class="fa fa-project-diagram menu__icon" aria-hidden="true"></i>
          Projects
        </router-link>
      </li>
    </ul>
  </div>

</template>

<script>

export default {
  name: 'Menu',

  data() {
    return {
      contextSection: '',
    };
  },

  methods: {
    highlightSection(section) {
      return {
        menu__link: true,
        'menu__link--active': section === this.contextSection,
      };
    },
  },
};
</script>
