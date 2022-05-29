<template>
  <div class="card px-0 mb-2">
    <div class="card-header py-2">
      <h4 class="card-heading">Upcoming</h4>
    </div>
    <div class="card-body">

      <div class="card reservation mb-3">
        <div class="row">
          <div class="col-md-4" >
            <img class="rentalPhoto" src="https://th.bing.com/th/id/R.28660b22fdb6af124a392721bae3988a?rik=E3l51BvmuR5%2bmg&pid=ImgRaw&r=0" alt=""/>
          </div>
          <div class="col-md-4">Molim?</div>
          <div class="col-md-4">Ha?</div>
        </div>
      </div>
      <div class="card reservation mb-3">
        <div class="row">
          <div class="col-md-4" >
            <img class="rentalPhoto" src="https://i.pinimg.com/originals/5e/e8/d7/5ee8d79f04919f66785857f094255b4d.jpg" alt=""/>
          </div>
          <div class="col-md-4">Molim?</div>
          <div class="col-md-4">Ha?</div>
        </div>
      </div>
      Rezervacija ili NONE
    </div>
  </div>
  <div class="card px-0 mb-5">
    <div class="card-header py-2">
      <h4 class="card-heading">Reservation history</h4>
    </div>
    <div class="card-body">
      Rezervacija ili NONE
    </div>
  </div>
</template>

<script>
export default {
  name: "ClientReservations"
}
</script>

<style scoped>
div.reservation {
  box-shadow: 0.1rem 0.1rem 0.4rem rgb(0 0 0 / 15%);
  background-color: #ffffff;
}
div.reservation:hover {
  padding: 3px;
}
img.rentalPhoto {
  height: 20vh;
  width: 100%;
  border-radius: 1rem;
  object-fit: cover;
  object-position: center;
}
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  height: fit-content;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: none;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  border-radius: 1rem;
}
.card-header:first-child {
  border-radius: calc(1rem - 1px) calc(1rem - 1px) 0 0;
}
.card-header {
  position: relative;
  padding: 2rem 2rem;
  border-bottom: none;
  background-color: inherit;
  box-shadow: 0 0.125rem 0.25rem rgb(0 0 0 / 8%);
  z-index: 2;
}
</style>