function initializeAll() {
    document.getElementById('accident-filters-list').addEventListener("click", function(event){
        event.preventDefault()
    });
    document.getElementById('climate-filters-list').addEventListener("click", function(event){
        event.preventDefault()
    });

    document.getElementById('location-filters-list').addEventListener("click", function(event){
        event.preventDefault()
    });

    document.getElementById('time-filters-list').addEventListener("click", function(event){
        event.preventDefault()
    });

    getPredictors().then(result => {
        document.getElementById('predictor-for-risk').innerHTML = ''
        result.forEach(predictor => {
            document.getElementById('predictor-for-risk').innerHTML += `<option value="${predictor}">${predictor}</option>`
        })
    })

    $(function () {
        $('#time-date-filter').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });

    $(function () {
        $('#time-time-filter').datetimepicker({
            format: 'LT'
        });
    });

    initializeFilters();
}

window.onload = initializeAll;