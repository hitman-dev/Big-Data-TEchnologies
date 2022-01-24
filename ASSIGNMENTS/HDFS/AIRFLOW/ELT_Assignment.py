from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.dummy import DummyOperator

dag = DAG(
    dag_id='210940125022_ELT',
    schedule_interval='0 0 * * *',
    start_date=datetime(2021, 1, 1),
    catchup=False,
    dagrun_timeout=timedelta(minutes=60),
    tags=['edbda'],
    params={"example_key": "example_value"},
)

extractionProcess = BashOperator(task_id="extractionProcess",
                                 bash_command="echo 1",
                                 dag=dag)

transformationProcess = BashOperator(task_id="transformationProcess",
                                     bash_command="echo 2",
                                     dag=dag)

loadProcess = BashOperator(task_id="loadProcess",
                           bash_command="echo 3",
                           dag=dag)

extractionProcess >> transformationProcess >> loadProcess