from airflow.models import DAG
from airflow.operators.bash import BashOperator
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.utils.dates import days_ago

args = {
    'owner':'hitman'
}

dag = DAG(
    dag_id='210940125022_WCSpark',
    default_args=args,
    start_date=days_ago(1),
    schedule_interval=None,
    params={},
    tags=['edbda', 'spark', 'spark-submit']
)

submit = SparkSubmitOperator(
    task_id='SparkTask',
    name="airflow-spark",
    application='/home/hitman/DBDA_HOME/spark/examples/src/main/python/wordcount.py',
    application_args=['1'],
    env_vars={'PYSPARK_DRIVER_PYTHON':'python',
              'HADOOP_CONF_DIR':'/home/hitman/DBDA_HOME/hadoop-3.3.1/etc/hadoop',
              'PYSPARK_PYTHON':'/usr/bin/python3',
              'AIRFLOW_CONN_SPARK_DEFAULT':'local'},
    dag=dag
)

bashOps = BashOperator(
    task_id='bash_run',
    start_date=days_ago(1),
    bash_command='echo RunStarted',
    params=None,
    default_args={},
    dag=dag
)

bashOps2 = BashOperator(
    task_id='bash_run2',
    start_date=days_ago(1),
    bash_command='echo RunStarted2',
    params=None,
    default_args={},
    dag=dag
)

bashOps >> bashOps2 >> submit

