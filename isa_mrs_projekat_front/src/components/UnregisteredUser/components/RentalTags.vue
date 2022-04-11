<template>
    <div class="tags row ps-3 pe-1">
        <div v-for="(tag, i) in freeAdditionalServices" :key="i" class="tagDiv chip chip-outline btn-outline-primary me-2 px-3 my-1" data-ripple-color="dark">
          {{tag.name}}
        </div>
    </div>
</template>

<script>
export default {
  name: "RentalTags",
  props: ["additionalServices"],
  computed: {
    freeAdditionalServices() {
      let freeAdditionalServices = [];
      this.additionalServices.forEach(additionalService => additionalService.price === 0 ?freeAdditionalServices.push(additionalService) : null);
      return freeAdditionalServices;
    }
  }
}
</script>

<style scoped>
  .tagDiv {
    width: fit-content;
    border-radius: 30px;
    border: 1px solid darkblue;
    cursor: pointer;
  }
</style>