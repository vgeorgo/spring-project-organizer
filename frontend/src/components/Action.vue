<template>

  <router-link
    tag="button"
    :class="btnClass"
    :to="route">
    {{ label }}
  </router-link>

</template>

<script>
export default {
  name: 'Action',
  props: {
    type: String,
    route: String,
    label: String,
  },
  computed: {
    btnClass() {
      return `btn btn-${this.type} btn-sm`;
    },
  },
};
</script>
