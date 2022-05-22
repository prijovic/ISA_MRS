<template>
  <router-link :to="{path: to}" class="link" :class="{active: isActive}">
    <font-awesome-icon class="icon" :icon="icon"/>
    <transition name="fade">
            <span v-if="!collapsed">
        <slot/>
      </span>
    </transition>
  </router-link>
</template>

<script>
import {computed} from "vue";
import {collapsed} from "@/components/state";
import {useRoute} from "vue-router"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
  name: "MenuLink",
  components: {
    FontAwesomeIcon
  },
  props: {
    to: {type:String, required:true},
    icon: {type:String, required:true}
  },
  setup(props) {
    const route = useRoute();
    const isActive = computed(() => route.path === props.to)
    return {isActive, collapsed}
  }
}
</script>

<style scoped>
  .fade-enter-active, .fade-leave-active {
    transition: 0.1s;
  }

  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

  .link {
    display: flex;
    align-items: center;

    cursor: pointer;
    position: relative;
    font-weight: 400;
    user-select: none;

    margin: 0.1em 0;
    padding: 0.2em;
    border-radius: 0.25em;
    height: 2em;

    color: white;
    text-decoration: none;
  }

  .link:hover, .link:active, .link:focus {
    color: #008970;
    background-color: white;
  }

  .link .icon {
    flex-shrink: 0;
    margin-right: 10px;
  }
</style>