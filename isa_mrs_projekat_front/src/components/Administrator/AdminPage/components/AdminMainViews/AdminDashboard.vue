<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10">
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total reservations</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalReservations}}</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last year</p>
          <p class="d-table-row text-center fw-bold fs-3">{{reservationsLastYear}}</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last month</p>
          <p class="d-table-row text-center fw-bold fs-3">{{reservationsLastMonth}}</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Reservations last week</p>
          <p class="d-table-row text-center fw-bold fs-3">{{reservationsLastWeek}}</p>
        </div>
      </div>
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total owners</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalOwners}}</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total clients</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalClients}}</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total vacation houses</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalVacationRentals}}</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total boats</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalBoats}}</p>
        </div>
      </div>
      <div class="row justify-content-between">
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Total adventures</p>
          <p class="d-table-row text-center fw-bold fs-3">{{totalAdventures}}</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last year</p>
          <p class="d-table-row text-center fw-bold fs-3">{{ incomeLastYear.toFixed(1) }}$</p>
        </div>
        <div class="col d-table p-3 me-5 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last month</p>
          <p class="d-table-row text-center fw-bold fs-3">{{ incomeLastMonth.toFixed(1) }}$</p>
        </div>
        <div class="col d-table p-3 container rounded shadow mt-5">
          <p class="d-table-row text-center fs-5">Income last week</p>
          <p class="d-table-row text-center fw-bold fs-3">{{ incomeLastWeek.toFixed(1) }}$</p>
        </div>
      </div>
      <div class="row">
        <div class="container d-flex justify-content-center p-3 rounded shadow mt-5 mb-5">
          <div class="row">
            <div class="my-auto mx-auto">
              <income-rate-view></income-rate-view>
            </div>
          </div>
          <Line :chart-data="chartData" :key="chartData.datasets[0].data"></Line>
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
import axios from "axios";
import {useStore} from "vuex";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, LineElement, PointElement, LineController)

export default {
  name: "AdminContentPage",
  components: {IncomeRateView, Line},
  data() {
    return {
      totalReservations: 0,
      reservationsLastYear: 0,
      reservationsLastMonth: 0,
      reservationsLastWeek: 0,
      totalOwners: 0,
      totalClients: 0,
      totalVacationRentals: 0,
      totalBoats: 0,
      totalAdventures: 0,
      incomeLastYear: 0,
      incomeLastMonth: 0,
      incomeLastWeek: 0,
      chartData: {
        labels: [],
        datasets: [
          {
            label: 'Reservation income',
            backgroundColor: '#008970',
            borderColor: 'rgb(0, 137, 112, 0.4)',
            data: []
          },
          {
            label: 'Cancellation income',
            backgroundColor: '#e23c52',
            borderColor: 'rgb(226, 60, 82, 0.4)',
            data: []
          }
        ]
      }
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Profits/adminDashboard", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      }
    })
    .then((response) => {
      const dashboardData = response.data;
      this.totalAdventures = dashboardData.totalAdventures;
      this.totalReservations = dashboardData.totalReservations;
      this.reservationsLastYear = dashboardData.reservationsLastYear;
      this.reservationsLastMonth = dashboardData.reservationsLastMonth;
      this.reservationsLastWeek = dashboardData.reservationsLastWeek;
      this.totalOwners = dashboardData.totalOwners;
      this.totalClients = dashboardData.totalClients;
      this.totalVacationRentals = dashboardData.totalVacationRentals;
      this.totalBoats = dashboardData.totalBoats;
      this.incomeLastYear = dashboardData.incomeLastYear;
      this.incomeLastMonth = dashboardData.incomeLastMonth;
      this.incomeLastWeek = dashboardData.incomeLastWeek;
      const nodes1 = dashboardData.reservationIncomeGraph.nodes;
      const nodes2 = dashboardData.cancellationIncomeGraph.nodes;
      let dataset1 = [];
      let dataset2 = [];
      for (let i = 0; i < 12; i ++) {
        this.chartData.labels.push(nodes1[i].month);
        dataset1.push(nodes1[i].value);
        dataset2.push(nodes2[i].value);
      }
      this.chartData.datasets[0].data = dataset1;
      this.chartData.datasets[1].data = dataset2;
    })
    .catch((e) => {
      console.log(e);
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
    })
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