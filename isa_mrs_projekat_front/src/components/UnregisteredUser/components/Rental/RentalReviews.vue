<template>
 <div></div>
</template>

<script>
export default {
  name: "RentalReviews",
  props: ["reviewsPage"],
  data() {
    return {

    }
  },
  mounted() {
    this.reviews = this.reviewsPage.content;
    this.currentPage = this.reviewsPage.currentPage;
    this.totalPages = this.reviewsPage.pages;
    this.pageSize = this.reviewsPage.pageSize;
  },
  computed: {

  },
  methods: {

  }
}
</script>

<style scoped>

</style>