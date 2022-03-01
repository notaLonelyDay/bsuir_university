import flask
from flask import Flask, request, render_template, send_from_directory

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
    text = request.args.get("text")
    key = request.args.get("key")
    cypher = request.args.get("cypher")
    return text, key, cypher


@app.route("/api/encrypt", methods=["POST"])
def encrypt():
    text, key, cypher = get_params()
    ans = cyphers[int(cypher)].encrypt(text, key)
    return ans


@app.route("/api/decrypt", methods=["POST"])
def decrypt():
    text, key, cypher = get_params()
    ans = cyphers[int(cypher)].decrypt(text, key)
    return ans


if __name__ == '__main__':
    app.run(debug=True)
