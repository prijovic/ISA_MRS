<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10">
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total reservations</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last year</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last month</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last week</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
      </div>
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total owners</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total clients</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total vacation houses</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total boats</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
      </div>
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total adventures</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last year</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last month</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last week</p>
          <p class="d-table-row text-center fw-bold fs-3">XYZ</p>
        </div>
      </div>
      <div class="row">
        <div class="container p-3 rounded shadow mt-5">
          <Line :chart-data="chartData" :height="400" :width="400"></Line>
        </div>
      </div>
      <div class="row">
        <div class="col-3 mt-5 mb-5">
          <income-rate-view></income-rate-view>
        </div>
        <div class="col-9 mt-5">
          <div class="container rounded" style="border: #008970 solid 1px">
            <p>USER CATEGORIES</p>
          </div>
        </div>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
</template>

<script>
import IncomeRateView
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminIncomeRateComponents/IncomeRateView";
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, LineElement, PointElement, LineController } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, LineElement, PointElement, LineController)

export default {
  name: "AdminContentPage",
  components: {IncomeRateView, Line},
  data() {
    return {
      chartData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August'],
        datasets: [
          {
            label: 'Reservation income',
            backgroundColor: '#008970',
            borderColor: 'rgb(0, 137, 112, 0.4)',
            data: [18, 20, 12, 6, 10, 4, 5, 15]
          },
          {
            label: 'Cancellation income',
            backgroundColor: '#e23c52',
            borderColor: 'rgb(226, 60, 82, 0.4)',
            data: [6, 2, 3, 4, 5, 8, 10, 9]
          }
        ]
      }
    }
  }
}

</script>

<style scoped>
  p {
    color: #008970;
    user-select: none; /* supported by Chrome and Opera */
    -webkit-user-select: none; /* Safari */
    -moz-user-select: none; /* Firefox */
    -ms-user-select: none; /* Internet Explorer/Edge */
  }


</style>