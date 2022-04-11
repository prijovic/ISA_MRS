<template>
    <div class="row description">
        <div class="lineTitle">
            <p class="h4">Description</p>
            <hr class="ms-1">
        </div>
        <p class="ps-3" style="color: grey;">{{description}}</p>
    </div>
</template>

<script>
export default {
  name: "RentalDescription",
  props: ["description"]
}
</script>

<style scoped>
  div.description {
    text-align: justify;
  }

  div.lineTitle {
    display: flex;
  }

  hr {
    flex-grow: 1;
  }
</style>