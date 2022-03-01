import flask
from flask import Flask, request, render_template, send_from_directory

from crypto.base import CryptoException
from crypto.column import ColumnCrypt
from crypto.decimation import DecimationCrypt
from crypto.vigener import VigenerCrypt

app = Flask(__name__)

cyphers = [
    ColumnCrypt,
    DecimationCrypt,
    VigenerCrypt
]


@app.route("/")
def hello_world():
    return render_template("index.html")


@app.route('/js/<path:path>')
def send_js(path):
    return send_from_directory('templates', path)


def get_params():
    text = request.form['text']
    key = request.form['key']
    cypher = int(request.form['cypher'])
    return text, key, cypher


@app.route("/api/encrypt", methods=["POST"])
def encrypt():
    try:
        text, key, cypher = get_params()
        ans = cyphers[int(cypher)].encrypt(text, key)
    except CryptoException as e:
        return str(e), 500
    return ans


@app.route("/api/decrypt", methods=["POST"])
def decrypt():
    try:
        text, key, cypher = get_params()
        ans = cyphers[int(cypher)].decrypt(text, key)
    except CryptoException as e:
        return str(e), 500
    return ans


if __name__ == '__main__':
    app.run(debug=False)
