from datetime import datetime, timedelta

from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.dummy import DummyOperator

# Second approach for creating DAGs
dag1 = DAG(dag_id="210940125022_Bash",
           schedule_interval='0 0 * * *',
           start_date=datetime(2021, 1, 1),
           catchup=False,
           tags=['edbda', 'mydag'],
           params={"example_key": "example_value"}
           )

taskA = BashOperator(task_id="TaskA",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskB = BashOperator(task_id="TaskB",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskC = BashOperator(task_id="TaskC",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskD = BashOperator(task_id="TaskD",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskE = BashOperator(task_id="TaskE",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskF = BashOperator(task_id="TaskF",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskG = BashOperator(task_id="TaskG",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskH = BashOperator(task_id="TaskH",
                     bash_command="echo dbda",
                     cwd="/tmp",
                     dag=dag1)

taskEnd = BashOperator(task_id="TaskEnd",
                       bash_command="echo dbda",
                       cwd="/tmp",
                       dag=dag1)


taskA >> taskB >> taskEnd
taskA >> taskC >> taskF
taskA >> taskD >> taskF
taskA >> taskE >> taskF
taskF >> taskG >> taskEnd
taskF >> taskH >> taskEnd




